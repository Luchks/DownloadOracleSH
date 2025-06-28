#!/usr/bin/env bash

USER="system"
PASS="Oracle123"
HOST="localhost"
PORT="1521"
SID="XE"  # Confirmado

echo "🔌 Conectando a Oracle con usql (SID: $SID)..."
usql "oracle://${USER}:${PASS}@${HOST}:${PORT}/${SID}"
