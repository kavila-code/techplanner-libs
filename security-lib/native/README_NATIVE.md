Building the native library

This project provides a small JNI-native implementation used by `NativeSecurity`.

Requirements:

- JDK installed and `JAVA_HOME` set (or adjust include paths in the Makefile)
- `gcc`/`clang` for Linux/macOS, or MinGW/MSYS for Windows

Linux / macOS (with GNU toolchain):

1. Ensure `JAVA_HOME` is set (e.g. `/usr/lib/jvm/java-21-openjdk`)
2. From this directory run:

```sh
make
```

This produces `libsecurity_native.so` (Linux) or `libsecurity_native.dylib` (macOS).

Windows (MinGW):

```bat
:: from MinGW shell
make

:: if make is not available, compile directly
gcc -shared -I%JAVA_HOME%/include -I%JAVA_HOME%/include/win32 -o security_native.dll security_native.c
```

Loading the library from Java

Place the produced shared library on the JVM library path or set `-Djava.library.path` when running the application, for example:

```sh
java -Djava.library.path=./native -jar your-app.jar
```
