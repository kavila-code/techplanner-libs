CC = gcc
CFLAGS = -std=c99 -Wall -Wextra -O2 -Isrc/main/include
PATH := C:/msys64/ucrt64/bin;$(PATH)
SRC = src/main/c/models.c src/main/c/service.c src/main/c/validator.c src/test/c/main.c
TARGET = target/recommendation-lib.exe

.PHONY: all clean run

all: $(TARGET)

$(TARGET): $(SRC)
	@mkdir target 2>NUL || true
	$(CC) $(CFLAGS) -o $@ $^

run: $(TARGET)
	./$(TARGET)

clean:
	rm -f $(TARGET)
