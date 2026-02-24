#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD GREEN phase commit
#  Use this AFTER implementing just enough code to make the tests pass.
#  ALL tests must pass for this commit to be accepted.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}[TDD] Running tests to verify GREEN phase...${NC}"
echo ""

mvn test 2>&1
EXIT_CODE=$?

if [ $EXIT_CODE -ne 0 ]; then
    echo ""
    echo -e "${RED}✗ COMMIT BLOCKED${NC}"
    echo ""
    echo "  Tests are still failing. GREEN phase requires all tests to pass."
    echo "  Fix the failing tests above, then run: ./commit-green.sh"
    echo ""
    exit 1
fi

echo ""
echo -e "${GREEN}✓ All tests pass (GREEN phase confirmed)${NC}"
echo ""

echo -e "${YELLOW}Describe what you implemented (e.g. 'register saves user with hashed password'):${NC}"
read -r DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo "Description cannot be empty."
    exit 1
fi

MESSAGE="[GREEN] $DESCRIPTION"

git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}✓ GREEN commit saved: \"$MESSAGE\"${NC}"
echo ""
echo "  Next step: clean up your code without breaking any tests,"
echo "  then run: ./commit-refactor.sh"
echo "  (or skip straight to the next RED phase if no cleanup needed)"
echo ""
