#!/bin/shell

RED='\033[0;31m'
GREEN='\033[0;32m'
BLUE='\033[0;34m'
BOLD='\033[1m'

source ~/.bashrc

# Obtain the package name
PACKAGE_NAME=$(cat app/build.gradle.kts | grep applicationId | awk -F '"' '{print $2}')

# check if we find a package
if [ -z $PACKAGE_NAME ]; then
	echo -e "${BOLD}${RED}Can't find the package${RESET} check app/build.gradle.kts"
	exit 1
fi

echo -e "Package $PACKAGE_NAME ${BOLD}${BLUE}finded${RESET}"

echo "Compiling and installing the app"
./gradlew installDebug

# Check the build status
if [ $? -ne 0 ]; then
	echo "Build failed"
	exit 1
fi

echo -e "${BOLD}${BLUE}Executing${RESET} on emualtor"
adb shell am start -n $PACKAGE_NAME/.MainActivity

echo -e "${BOLD}${GREEN}App launched :D${RESET}"
