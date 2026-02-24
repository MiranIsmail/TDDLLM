#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  Run this ONCE at the start of the experiment to set up git.
#  After this, use commit-red.sh, commit-green.sh, commit-refactor.sh
# ─────────────────────────────────────────────────────────────

GREEN='\033[0;32m'
CYAN='\033[0;36m'
NC='\033[0m'

echo ""
echo -e "${CYAN}Setting up experiment repository...${NC}"

# Init git
git init
git config user.email "participant@experiment.local"
git config user.name "Participant"

# Make commit scripts executable
chmod +x commit-red.sh commit-green.sh commit-refactor.sh

# Initial commit of the skeleton
git add -A
git commit -m "[SETUP] experiment skeleton — unimplemented stubs"

echo ""
echo -e "${GREEN}✓ Ready. Your TDD commit workflow:${NC}"
echo ""
echo "   1. ./commit-red.sh       — after writing a test (tests must FAIL)"
echo "   2. ./commit-green.sh     — after implementing code (tests must PASS)"
echo "   3. ./commit-refactor.sh  — after cleaning up (tests must still PASS)"
echo ""
echo "   Repeat for each task."
echo ""
