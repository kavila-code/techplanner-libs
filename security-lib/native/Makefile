JAVA_HOME_MSYS := $(shell if command -v cygpath >/dev/null 2>&1; then cygpath -u "$(JAVA_HOME)"; else printf '%s' "$(JAVA_HOME)"; fi)
UNAME := $(shell uname -s)

ifeq ($(findstring MINGW,$(UNAME)),MINGW)
	LIB_NAME = security_native.dll
	JAVA_INCLUDE = $(JAVA_HOME_MSYS)/include
	JAVA_PLATFORM_INCLUDE = $(JAVA_INCLUDE)/win32
	CC ?= gcc
	CFLAGS ?= -shared -O2
	TARGET = $(LIB_NAME)
else ifeq ($(UNAME),Darwin)
	LIB_NAME = libsecurity_native.dylib
	JAVA_INCLUDE = $(JAVA_HOME_MSYS)/include
	JAVA_PLATFORM_INCLUDE = $(JAVA_INCLUDE)/darwin
	CC ?= clang
	CFLAGS ?= -dynamiclib -fPIC -O2
	TARGET = $(LIB_NAME)
else
	LIB_NAME = libsecurity_native.so
	JAVA_INCLUDE = $(JAVA_HOME_MSYS)/include
	JAVA_PLATFORM_INCLUDE = $(JAVA_INCLUDE)/linux
	CC ?= gcc
	CFLAGS ?= -shared -fPIC -O2
	TARGET = $(LIB_NAME)
endif

all: $(TARGET)

$(TARGET): security_native.c
	$(CC) $(CFLAGS) -I"$(JAVA_INCLUDE)" -I"$(JAVA_PLATFORM_INCLUDE)" -o $@ $<

clean:
	rm -f security_native.dll libsecurity_native.so libsecurity_native.dylib
