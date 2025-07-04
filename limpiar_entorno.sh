#!/bin/bash

echo "🧹 Limpiando configuración de Neovim, Lazy.nvim, jdtls y Eclipse..."
sleep 1

# NEOVIM
rm -rf ~/.config/nvim
rm -rf ~/.local/share/nvim
rm -rf ~/.cache/nvim

# Lazy.nvim y packer u otros gestores si los usaste antes
rm -rf ~/.local/share/lazy
rm -rf ~/.local/share/nvim/site/pack

# JDTLS y lenguaje Java LSP
rm -rf ~/jdt-language-server*
rm -rf ~/.local/share/java

# ECLIPSE manual
rm -rf ~/.eclipse
rm -rf ~/.p2
rm -rf ~/opt/eclipse
rm -rf ~/eclipse
rm -rf ~/.local/share/applications/eclipse.desktop

# WAR y proyectos generados (opcionales: descomenta si quieres borrar)
# echo "🛑 También eliminaré tus proyectos locales si confirmas..."
# read -p "¿Eliminar proyectos como ~/mi-proyecto, ~/oracle, ~/workspace? (s/N): " confirm
# if [[ "$confirm" =~ ^[sS]$ ]]; then
#     rm -rf ~/mi-proyecto ~/oracle ~/workspace ~/proyectEclipse ~/myScala ~/pernosnilton
#     echo "✅ Proyectos eliminados."
# fi

echo "✅ Limpieza completada. El entorno de desarrollo está vacío."

