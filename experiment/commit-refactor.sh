#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD REFACTOR phase commit
#  Use this AFTER cleaning up your implementation.
#  Tests must still all pass — refactoring must not break behaviour.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}[TDD] Running tests to verify REFACTOR phase...${NC}"
echo ""

mvn test -q 2>&1
EXIT_CODE=$?

if [ $EXIT_CODE -ne 0 ]; then
    echo ""
    echo -e "${RED}✗ COMMIT BLOCKED${NC}"
    echo ""
    echo "  Tests are failing after refactoring — your changes broke something."
    echo "  Refactoring must not change external behaviour."
    echo "  Fix the failures above, then run: ./commit-refactor.sh"
    echo ""
    exit 1
fi

echo ""
echo -e "${GREEN}✓ All tests still pass (REFACTOR phase confirmed)${NC}"
echo ""

echo -e "${YELLOW}Describe what you refactored (e.g. 'extracted token generation to helper method'):${NC}"
read -r DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo "Description cannot be empty."
    exit 1
fi

MESSAGE="[REFACTOR] $DESCRIPTION"

git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}✓ REFACTOR commit saved: \"$MESSAGE\"${NC}"
echo ""
echo "  One full TDD cycle complete. Ready for the next RED phase."
echo "  Run: ./commit-red.sh  to start the next cycle."
echo ""
