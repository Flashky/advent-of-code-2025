#!/bin/bash
# =================================================================================
# Usage:       sourced by the *.test.sh scripts
# Description: Harness for the workflow script tests. It puts a stub `gh` first on
#              PATH, records every call and lets each test answer them with a
#              `gh_handler` function, so no request leaves the runner. stdout and
#              stderr are captured apart: a script's stdout is a contract (the
#              caller captures it), warnings and errors are not.
# =================================================================================

FAILURES=0

setup_stub() {
  TEST_WORK_DIR=$(mktemp -d)
  trap 'rm -rf "$TEST_WORK_DIR"' EXIT
  export GH_CALLS="$TEST_WORK_DIR/calls"
  : > "$GH_CALLS"

  mkdir -p "$TEST_WORK_DIR/bin"
  export GH_STDIN="$TEST_WORK_DIR/stdin"
  : > "$GH_STDIN"
  cat > "$TEST_WORK_DIR/bin/gh" <<'STUB'
#!/bin/bash
printf '%s\n' "$*" >> "$GH_CALLS"
# `gh api --input -` reads the body from stdin; keep it so a test can assert what
# was sent, and consume it either way or the producer dies with SIGPIPE.
case " $* " in
  *" --input - "*) cat > "$GH_STDIN" ;;
esac
if declare -F gh_handler > /dev/null; then
  gh_handler "$@"
  exit $?
fi
exit "${GH_STATUS:-0}"
STUB
  chmod +x "$TEST_WORK_DIR/bin/gh"
  export PATH="$TEST_WORK_DIR/bin:$PATH"
}

# run_script <script> [arguments]...: leaves the status in RUN_STATUS, the stdout
# in RUN_STDOUT and the stderr in RUN_STDERR.
run_script() {
  local stderr_file
  stderr_file=$(mktemp)
  set +e
  RUN_STDOUT=$(bash "$@" 2> "$stderr_file")
  RUN_STATUS=$?
  set -e
  RUN_STDERR=$(cat "$stderr_file")
  rm -f "$stderr_file"
}

pass() {
  echo "ok   $1"
}

fail() {
  echo "FAIL $1: $2"
  FAILURES=$((FAILURES + 1))
}

# expect_status <name> <expected-exit>
expect_status() {
  if [ "$RUN_STATUS" -eq "$2" ]; then
    pass "$1: exit"
  else
    fail "$1" "exit $RUN_STATUS (esperado $2) -> stdout: $RUN_STDOUT · stderr: $RUN_STDERR"
  fi
}

# expect_stdout <name> <fragment>
expect_stdout() {
  if [[ "$RUN_STDOUT" == *"$2"* ]]; then
    pass "$1: stdout"
  else
    fail "$1" "stdout no contiene '$2' -> $RUN_STDOUT"
  fi
}

# expect_stdout_is <name> <exact>: the whole stdout must match, so a stray line
# next to the answer does not pass.
expect_stdout_is() {
  if [ "$RUN_STDOUT" = "$2" ]; then
    pass "$1: stdout exacto"
  else
    fail "$1" "stdout '$RUN_STDOUT' (esperado exactamente '$2')"
  fi
}

# expect_no_stdout <name> <fragment>
expect_no_stdout() {
  if [[ "$RUN_STDOUT" == *"$2"* ]]; then
    fail "$1" "stdout contiene '$2' -> $RUN_STDOUT"
  else
    pass "$1: stdout limpio"
  fi
}

# expect_stderr <name> <fragment>
expect_stderr() {
  if [[ "$RUN_STDERR" == *"$2"* ]]; then
    pass "$1: stderr"
  else
    fail "$1" "stderr no contiene '$2' -> $RUN_STDERR"
  fi
}

# expect_no_stderr <name>
expect_no_stderr() {
  if [ -z "$RUN_STDERR" ]; then
    pass "$1: sin stderr"
  else
    fail "$1" "stderr no está vacío -> $RUN_STDERR"
  fi
}

# expect_stdin <name> <jq expression that must hold>
expect_stdin() {
  if jq -e "$2" "$GH_STDIN" > /dev/null 2>&1; then
    pass "$1: cuerpo"
  else
    fail "$1" "el cuerpo enviado no cumple '$2' -> $(cat "$GH_STDIN" 2>/dev/null)"
  fi
}

# expect_call <name> <exact `gh` invocation>
expect_call() {
  if grep -Fxq -- "$2" "$GH_CALLS"; then
    pass "$1: llamada"
  else
    fail "$1" "no llamó a 'gh $2' -> $(tr '\n' '|' < "$GH_CALLS")"
  fi
}

# expect_call_fragment <name> <fragment>: the invocation is partly unknown (a jq
# filter with quotes), so only the part that identifies the endpoint is matched.
expect_call_fragment() {
  if grep -Fq -- "$2" "$GH_CALLS"; then
    pass "$1: llamada"
  else
    fail "$1" "ninguna llamada contiene '$2' -> $(tr '\n' '|' < "$GH_CALLS")"
  fi
}

# expect_no_call <name> <fragment>
expect_no_call() {
  if grep -Fq -- "$2" "$GH_CALLS"; then
    fail "$1" "llamó a 'gh $2' -> $(tr '\n' '|' < "$GH_CALLS")"
  else
    pass "$1: sin llamada"
  fi
}

finish() {
  if [ "$FAILURES" -ne 0 ]; then
    echo
    echo "$FAILURES FALLOS"
    exit 1
  fi
  echo
  echo "TODO OK"
}
