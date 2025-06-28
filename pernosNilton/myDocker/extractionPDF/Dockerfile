FROM python:3.11-slim

WORKDIR /app

RUN apt-get update && apt-get install -y \
    build-essential \
    libjpeg-dev \
    zlib1g-dev \
 && rm -rf /var/lib/apt/lists/*

COPY extract_pdf.py .

RUN pip install --no-cache-dir pdfplumber

CMD ["python", "extract_pdf.py"]
