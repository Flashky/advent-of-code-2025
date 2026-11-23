#!/bin/bash
# =================================================================================
# Usage:       ./check-required-secrets.test.sh
# Description: Checks that every empty value is reported by name and that a
#              complete set passes. Nothing is called, so no stub is needed.
# =================================================================================

set -euo pipefail

SCRIPT_DIR=$(cd "$(dirname "$0")" && pwd)
# shellcheck disable=SC1091
source "$SCRIPT_DIR/testlib.sh"

FIRST=one
SECOND=two
export FIRST SECOND
run_script "$SCRIPT_DIR/check-required-secrets.sh" FIRST SECOND
expect_status "conjunto completo" 0

unset SECOND
run_script "$SCRIPT_DIR/check-required-secrets.sh" FIRST SECOND
expect_status "un secreto ausente" 1
expect_stdout "un secreto ausente" "::error title=Missing Secret::SECOND secret is not set."
expect_no_stdout "un secreto ausente" "FIRST secret is not set."

unset FIRST
run_script "$SCRIPT_DIR/check-required-secrets.sh" FIRST SECOND
expect_status "ninguno presente" 1
expect_stdout "ninguno presente" "FIRST secret is not set."
expect_stdout "ninguno presente" "SECOND secret is not set."

run_script "$SCRIPT_DIR/check-required-secrets.sh"
expect_status "sin nombres" 1
expect_stdout "sin nombres" "No secret names given"

finish
