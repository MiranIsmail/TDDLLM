#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD RED phase — commit a failing test
#
#  Use this AFTER you have written your test(s) by hand,
#  BEFORE you prompt the LLM for any implementation code.
#
#  The script will BLOCK the commit if all tests pass —
#  that means you have already written the implementation,
#  which violates the TDD cycle.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  TDD  ▸  RED phase check${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo "  Running tests — expecting at least one failure..."
echo ""

# Run tests and capture full output so we can show it on failure
TEST_OUTPUT=$(mvn test 2>&1)
EXIT_CODE=$?

# ── GUARD: if ALL tests pass the cycle is wrong ──────────────
if [ $EXIT_CODE -eq 0 ]; then
    echo ""
    echo -e "${RED}  ✗  COMMIT BLOCKED — tests are passing${NC}"
    echo ""
    echo "  RED phase requires at least one failing test."
    echo "  You must write your test BEFORE implementing the code."
    echo ""
    echo "  Steps:"
    echo "   1. Write your test in the relevant test file"
    echo "   2. Run:  ./red.sh          (tests should fail here)"
    echo "   3. Prompt Claude with your failing test"
    echo "   4. Run:  ./green.sh        (tests should pass here)"
    echo ""
    exit 1
fi

# Show the relevant part of the maven output (last 30 lines is usually enough)
echo "$TEST_OUTPUT" | tail -30
echo ""
echo -e "${GREEN}  ✓  Tests are failing — RED phase confirmed${NC}"
echo ""

# ── Collect a short description from the developer ───────────
echo -e "${YELLOW}  What behaviour are you testing?${NC}"
echo "  (e.g. 'register returns 409 when username already exists')"
echo ""
read -r -p "  Description: " DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo ""
    echo "  Description cannot be empty. Aborting."
    exit 1
fi

# ── Commit ────────────────────────────────────────────────────
MESSAGE="[RED] $DESCRIPTION"
git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}  ✓  RED commit saved: \"$MESSAGE\"${NC}"
echo ""
echo "  Next:"
echo "   • Paste your failing test into Claude"
echo "   • Ask Claude to implement the code that makes it pass"
echo "   • Run:  ./green.sh  once the tests are green"
echo ""
