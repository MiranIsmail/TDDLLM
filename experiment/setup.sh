#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  Run this ONCE at the very start of the experiment.
#  It initialises git and makes the commit scripts executable.
#  After this, use: red.sh → green.sh → blue.sh
# ─────────────────────────────────────────────────────────────

GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}Setting up experiment repository...${NC}"
echo ""

# Initialise git if not already done
if [ ! -d ".git" ]; then
    git init
fi

git config user.email "participant@experiment.local"
git config user.name "Participant"

# Make all commit scripts executable
chmod +x red.sh green.sh blue.sh

# Record the initial skeleton as the baseline commit
git add -A
git commit -m "[SETUP] experiment skeleton — unimplemented stubs"

echo ""
echo -e "${GREEN}✓ Setup complete. Your TDD commit cycle:${NC}"
echo ""
echo "   1.  Write a test by hand"
echo "   2.  ./red.sh    — commit the failing test"
echo "   3.  Prompt Claude with the failing test, paste the code"
echo "   4.  ./green.sh  — commit once all tests pass"
echo "   5.  Refactor (optional, with or without Claude)"
echo "   6.  ./blue.sh   — commit the cleaned-up code"
echo ""
echo "   Repeat steps 1–6 for each task."
echo ""
