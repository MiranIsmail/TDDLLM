#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD BLUE (Refactor) phase — commit a cleaned-up implementation
#
#  Use this AFTER you have tidied the code (with or without
#  Claude's help).  All tests must still pass — refactoring
#  must never change external behaviour.
#
#  If nothing needed refactoring just run this script straight
#  away; it will record that the phase was completed.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
BLUE='\033[0;34m'
NC='\033[0m'

echo ""
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${BLUE}  TDD  ▸  BLUE (Refactor) phase check${NC}"
echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo "  Running tests — verifying that refactoring broke nothing..."
echo ""

# Run tests quietly (we only care about pass/fail here)
TEST_OUTPUT=$(mvn test 2>&1)
EXIT_CODE=$?

# ── GUARD: block if refactoring broke something ───────────────
if [ $EXIT_CODE -ne 0 ]; then
    echo "$TEST_OUTPUT" | tail -40
    echo ""
    echo -e "${RED}  ✗  COMMIT BLOCKED — refactoring broke one or more tests${NC}"
    echo ""
    echo "  Refactoring must not change external behaviour."
    echo ""
    echo "  Steps:"
    echo "   1. Read the failure output above"
    echo "   2. Undo your last change, or ask Claude to fix it"
    echo "   3. Run:  ./blue.sh  again once all tests are green"
    echo ""
    exit 1
fi

# Show the Maven summary
echo "$TEST_OUTPUT" | grep -E "Tests run:|BUILD"
echo ""
echo -e "${GREEN}  ✓  All tests still pass — BLUE phase confirmed${NC}"
echo ""

# ── Collect a short description from the developer ───────────
echo -e "${YELLOW}  Describe what you refactored (or type 'no changes' if you skipped):${NC}"
echo "  (e.g. 'extracted token generation to a helper method')"
echo ""
read -r -p "  Description: " DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo ""
    echo "  Description cannot be empty. Aborting."
    exit 1
fi

# ── Commit ────────────────────────────────────────────────────
MESSAGE="[BLUE] $DESCRIPTION"
git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}  ✓  BLUE commit saved: \"$MESSAGE\"${NC}"
echo ""
echo -e "  ${BLUE}One full TDD cycle complete.${NC}"
echo ""
echo "  Next:"
echo "   • Move to the next task"
echo "   • Write the next test by hand"
echo "   • Run:  ./red.sh  to start the next cycle"
echo ""
