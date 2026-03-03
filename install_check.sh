#!/bin/bash
# ─────────────────────────────────────────────────────────────
#  install_check.sh
#
#  Checks that Java 17+ and Maven are installed, then compiles
#  and starts the application.
#
#  Works on Linux and macOS.
#  Windows users: follow INSTALL_AND_RUN.md manually, or use
#  Git Bash to run this script.
# ─────────────────────────────────────────────────────────────

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

PASS=true

echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  Environment check${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""

# ── Check: Java ───────────────────────────────────────────────
if command -v java &>/dev/null; then
    JAVA_VER=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f1)
    # Handle old-style "1.x" versioning
    [ "$JAVA_VER" = "1" ] && JAVA_VER=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}' | cut -d'.' -f2)
    if [ "$JAVA_VER" -ge 17 ] 2>/dev/null; then
        echo -e "  ${GREEN}✓${NC}  Java $JAVA_VER found"
    else
        echo -e "  ${RED}✗${NC}  Java $JAVA_VER found but version 17+ is required"
        echo "      Download: https://adoptium.net"
        PASS=false
    fi
else
    echo -e "  ${RED}✗${NC}  Java not found"
    echo "      Download: https://adoptium.net"
    PASS=false
fi

# ── Check: Maven ─────────────────────────────────────────────
if command -v mvn &>/dev/null; then
    MVN_VER=$(mvn -version 2>&1 | head -1 | awk '{print $3}')
    echo -e "  ${GREEN}✓${NC}  Maven $MVN_VER found"
else
    echo -e "  ${RED}✗${NC}  Maven not found"
    echo "      Download: https://maven.apache.org/download.cgi"
    PASS=false
fi

echo ""

# ── Abort if requirements are not met ────────────────────────
if [ "$PASS" = false ]; then
    echo -e "${RED}  Prerequisites missing — see INSTALL_AND_RUN.md${NC}"
    echo ""
    exit 1
fi

echo -e "${GREEN}  All checks passed.${NC}"
echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  Compiling...${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""

mvn compile -q
if [ $? -ne 0 ]; then
    echo ""
    echo -e "${RED}  Compilation failed. Fix the errors above and try again.${NC}"
    echo ""
    exit 1
fi

echo -e "${GREEN}  Compiled successfully.${NC}"
echo ""
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo -e "${CYAN}  Starting application...${NC}"
echo -e "${CYAN}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
echo ""
echo "  Login page   →  http://localhost:8080/login"
echo "  Register     →  http://localhost:8080/register"
echo "  Admin panel  →  http://localhost:8080/admin"
echo ""
echo "  Press Ctrl+C to stop the server."
echo ""

# Start the app — this blocks until the user stops it
mvn exec:java -Dexec.mainClass="com.experiment.App" -q
