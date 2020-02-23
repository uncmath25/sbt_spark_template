SHELL := /bin/bash
.DEFAULT_GOAL := run
MAIN_CLASS := uncmath25.spark.template.Main
SPARK_NUMBER_OF_CORES := 4
PATH_TO_JAR := target/scala-2.11/spark-template_2.11-0.1.0-SNAPSHOT.jar
INPUT_FILE := $$(pwd)/data/sample_input_data.csv
OUTPUT_FILE := $$(pwd)/out.csv

.PHONY: clean
clean:
	@echo "*** Cleaning repo ***"
	@rm -rf ${OUTPUT_FILE}
	sbt clean

.PHONY: build
build: clean
	@echo "*** Compiling scala classes and packaging as jar ***"
	@rm -rf ${OUTPUT_FILE}
	sbt compile package

.PHONY: fat-build
fat-build: clean
	@echo "*** Compiling scala classes and packaging as fat jar ***"
	sbt compile assembly

.PHONY: run
run: build
	@echo "*** Submitting scala app to spark ***"
	spark-submit \
		--class ${MAIN_CLASS} \
		--master local[${SPARK_NUMBER_OF_CORES}] \
		--deploy-mode client \
		$$(pwd)/${PATH_TO_JAR} \
		${INPUT_FILE} \
		${OUTPUT_FILE}
