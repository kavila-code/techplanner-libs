FROM gcc:14

WORKDIR /app

COPY pricing.c .

RUN gcc -shared -o libpricing.so -fPIC pricing.c