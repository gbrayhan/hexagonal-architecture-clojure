#!/bin/bash

# Define la lista negra de directorios (relativos a la raíz del repositorio)
BLACKLIST_DIRS=(".github" ".idea" "path/another_directory" "node_modules" ".clj-kondo" ".lsp")

# Define la lista negra de archivos (relativos a la raíz del repositorio)
BLACKLIST_FILES=(
    ".dockerignore"
    ".lein-repl-history"
    "create.bash"
    "CODE_OF_CONDUCT.md"
    "README.md"
    "SECURITY.md"
    "CONTRIBUTING.md"
    "package-lock.json"
    "LICENSE"
    "show-content.bash"
    ".gitignore"
    "project-content.txt"  # Se añade para evitar procesar el archivo de salida
)

# Define la lista negra de extensiones de archivo (sin el punto)
BLACKLIST_EXTENSIONS=("exe" "dll" "bin" "tmp" "log" "md")

# Define el archivo de salida
OUTPUT_FILE="project-content.txt"

# Inicializar el archivo de salida (vaciarlo si ya existe)
> "$OUTPUT_FILE"

# Función para verificar si un archivo está dentro de alguno de los directorios en la lista negra
is_in_blacklisted_dir() {
    local file="$1"
    for dir in "${BLACKLIST_DIRS[@]}"; do
        if [[ "$file" == "$dir/"* ]]; then
            return 0
        fi
    done
    return 1
}

# Función para verificar si un archivo está en la lista negra de archivos
is_blacklisted_file() {
    local file="$1"
    for bl_file in "${BLACKLIST_FILES[@]}"; do
        if [[ "$file" == "$bl_file" ]]; then
            return 0
        fi
    done
    return 1
}

# Función para verificar si un archivo tiene una extensión que esté en la lista negra
has_blacklisted_extension() {
    local file="$1"
    local ext="${file##*.}"
    # Si el nombre del archivo no contiene un punto, se ignora esta validación
    if [[ "$file" == "$ext" ]]; then
        return 1
    fi
    for banned_ext in "${BLACKLIST_EXTENSIONS[@]}"; do
        if [[ "$ext" == "$banned_ext" ]]; then
            return 0
        fi
    done
    return 1
}

# Verificar que el directorio actual sea un repositorio Git
if ! git rev-parse --is-inside-work-tree >/dev/null 2>&1; then
    echo "Este script debe ejecutarse dentro de un repositorio Git." >> "$OUTPUT_FILE"
    exit 1
fi

# Obtener la lista de archivos que no están ignorados por .gitignore
git ls-files --cached --others --exclude-standard -z | while IFS= read -r -d '' file; do
    if is_in_blacklisted_dir "$file" || is_blacklisted_file "$file" || has_blacklisted_extension "$file"; then
        continue
    fi

    {
        echo "Archivo: $file"
        echo "Contenido:"
        echo "---"
        cat "$file"
        echo "---"
        echo -e "\n"
    } >> "$OUTPUT_FILE"
done
