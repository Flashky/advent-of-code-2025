#!/bin/bash
# =================================================================================
# Usage:       ./check-required-secrets.sh <NAME>...
# Description: Fails when any of the given environment variables is empty. Each
#              workflow passes its own list, so the set of secrets a job needs
#              stays visible in the workflow next to the env block that declares
#              the values, and only the loop lives here.
# Example:     ./check-required-secrets.sh PAT_TOKEN CODACY_API_TOKEN
# =================================================================================

set -uo pipefail

if [ "$#" -eq 0 ]; then
  echo "::error title=Invalid Argument::No secret names given to check."
  exit 1
fi

MISSING=0
for name in "$@"; do
  if [ -z "${!name:-}" ]; then
    echo "::error title=Missing Secret::$name secret is not set."
    MISSING=1
  fi
done

exit "$MISSING"
