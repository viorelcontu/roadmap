#!/bin/sh

for i in "$@"
do
  case $i in
    -s|--disable-security)
      echo "Running roadmap with security disabled"
      props="-Dspring.profiles.include=no-security"
      shift
    ;;
    default)
      props=""
      shift
    ;;

    *)printf "illegal option: $i\n"
      exit 1
    ;;
  esac
done

java -jar $props /opt/roadmap.jar