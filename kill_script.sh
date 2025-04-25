#!/bin/bash

# Function to check if port is free
is_port_free() {
  ! lsof -i:8080 &>/dev/null
}

# Find and kill the process using port 8080
pid=$(lsof -ti :8080)
if [ -n "$pid" ]; then
  kill -9 $pid
  echo "Killed process $pid using port 8080"
else
  echo "No process found using port 8080"
fi

while ! is_port_free; do
  echo "Waiting for port 8080 to be free..."
  sleep 1
done

# Restart the application
mvn spring-boot:run &
echo "Application started"