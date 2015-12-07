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
		if not line.startswith("#") and not line.strip() == "":
			x = line.strip().split(" ")
			chunked = "".join(x)
			buffer += "%s" % (chunked.upper())
		else:
			buffer += ""+line

	output.write(buffer)
	output.close()
	input.close()

if __name__ == '__main__':
	main(sys.argv)
