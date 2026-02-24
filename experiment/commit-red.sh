#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD RED phase commit
#  Use this AFTER writing a test, BEFORE writing any implementation.
#  The tests MUST fail for this commit to be accepted.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}[TDD] Running tests to verify RED phase...${NC}"
echo ""

# Run tests, suppressing most output but capturing exit code
mvn test -q 2>&1
EXIT_CODE=$?

if [ $EXIT_CODE -eq 0 ]; then
    echo ""
    echo -e "${RED}✗ COMMIT BLOCKED${NC}"
    echo ""
    echo "  All tests passed — but RED phase requires at least one failing test."
    echo "  Make sure you have written your test BEFORE implementing the code."
    echo ""
    echo "  Steps:"
    echo "   1. Write your test in the appropriate test file"
    echo "   2. Run: ./commit-red.sh   (tests should fail here)"
    echo "   3. Implement the code"
    echo "   4. Run: ./commit-green.sh (tests should pass here)"
    echo ""
    exit 1
fi

echo ""
echo -e "${GREEN}✓ Tests are failing as expected (RED phase confirmed)${NC}"
echo ""

# Prompt for a short description
echo -e "${YELLOW}Describe what you are testing (e.g. 'register returns 201 on success'):${NC}"
read -r DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo "Description cannot be empty."
    exit 1
fi

TASK=$(basename "$PWD")
TIMESTAMP=$(date '+%H:%M')
MESSAGE="[RED] $DESCRIPTION"

git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}✓ RED commit saved: \"$MESSAGE\"${NC}"
echo ""
echo "  Next step: implement the code that makes the test pass,"
echo "  then run: ./commit-green.sh"
echo ""
