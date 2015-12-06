# -*- encoding:utf-8 -*-
import sys
import os
import time
import shutil

def main(argv):
	input_file_name = "rsa_entrada.txt"
	output_file_name = "rsa_saida.txt"

	input = open(input_file_name, "r")
	output = open(output_file_name, "w")

	buffer = ""
	output_name = ""

	for line in input:
		if not line.strip().startswith("[") and not line.strip().startswith("Teste") and not line.strip() == "" and not line.strip().startswith("Resposta"):
			varname = line.split("=")[0].strip()
			x = line.split("=")[1].strip()
			chunks_nr, chunk_size = len(x), 8
			chunked = " ".join([ x[i:i+chunk_size] for i in range(0, chunks_nr, chunk_size) ])
			buffer += "%s = \n%s\n" % (varname, chunked.upper())
		else:
			buffer += line

	output.write(buffer)
	output.close()
	input.close()

if __name__ == '__main__':
	main(sys.argv)
