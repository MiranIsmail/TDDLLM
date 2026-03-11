#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  TDD GREEN phase — commit a passing implementation
#
#  Use this AFTER you have pasted Claude's generated code into
#  your project and ALL tests pass.
#
#  The script will BLOCK the commit if any test is still failing.
#  Keep iterating with Claude until everything is green.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  TDD  ▸  GREEN phase check${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo "  Running tests — all must pass to proceed..."
echo ""

# Run tests and capture output
TEST_OUTPUT=$(mvn test 2>&1)
EXIT_CODE=$?

# ── GUARD: block if any test is still failing ─────────────────
if [ $EXIT_CODE -ne 0 ]; then
    # Show the failure output so the developer knows what to fix
    echo "$TEST_OUTPUT" | tail -40
    echo ""
    echo -e "${RED}  ✗  COMMIT BLOCKED — tests are still failing${NC}"
    echo ""
    echo "  GREEN phase requires every test to pass."
    echo ""
    echo "  Steps:"
    echo "   1. Copy the failure output above"
    echo "   2. Paste it into Claude: 'Tests failed with this output: [paste]. Please fix.'"
    echo "   3. Replace your implementation with Claude's updated code"
    echo "   4. Run:  ./green.sh  again"
    echo ""
    exit 1
fi

# Show the summary line from Maven
echo "$TEST_OUTPUT" | grep -E "Tests run:|BUILD"
echo ""
echo -e "${GREEN}  ✓  All tests pass — GREEN phase confirmed${NC}"
echo ""

# ── Collect a short description from the developer ───────────
echo -e "${YELLOW}  Describe what you implemented:${NC}"
echo "  (e.g. 'register saves user with bcrypt-hashed password')"
echo ""
read -r -p "  Description: " DESCRIPTION

if [ -z "$DESCRIPTION" ]; then
    echo ""
    echo "  Description cannot be empty. Aborting."
    exit 1
fi

# ── Commit ────────────────────────────────────────────────────
MESSAGE="[GREEN] $DESCRIPTION"
git add -A
git commit -m "$MESSAGE"

echo ""
echo -e "${GREEN}  ✓  GREEN commit saved: \"$MESSAGE\"${NC}"
echo ""
echo "  Next:"
echo "   • Review the code for clarity, duplication, or anything to clean up"
echo "   • Optionally ask Claude to refactor without breaking tests"
echo "   • Run:  ./blue.sh  when you are happy with the code"
echo "   • (If there is nothing to refactor you may still run ./blue.sh)"
echo ""
