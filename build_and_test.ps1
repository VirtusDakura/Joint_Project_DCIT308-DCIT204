$srcFiles = Get-ChildItem -Path src\main\java -Filter *.java -Recurse | ForEach-Object { $_.FullName }
if (!(Test-Path "bin")) {
    New-Item -ItemType Directory -Path "bin" | Out-Null
}
Write-Host "Compiling source files with javac..."
javac -d bin -encoding UTF-8 $srcFiles
if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilation successful! Running TestRunner..."
    java -cp bin org.ug.dsa.TestRunner
} else {
    Write-Host "Compilation failed with exit code $LASTEXITCODE"
}
