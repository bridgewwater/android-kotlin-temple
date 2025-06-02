.PHONY: dist test build

ifeq ($(OS),Windows_NT)
  ROOT_PWD=$(shell powershell -Command '(Get-Location).Path')
  ENV_GRADLE_WRAPPER_EXEC=.\gradlew.bat
else
  ROOT_PWD=$(shell pwd)
  ENV_GRADLE_WRAPPER_EXEC=./gradlew
endif

include z-android-base.mk
include plugin/z-plugin.mk
include demo/z-demo.mk

.PHONY: dep
dep: pluginDependImplementation demoDependImplementation

.PHONY: test
test: pluginTestDebug demoTestDebug

.PHONY: test.coverage
test.coverage: pluginJacocoReportDebug

.PHONY: assemble.debug
assemble.debug: pluginAssembleDebug demoAssembleDebug

.PHONY: ci.prepare
ci.prepare: env init

.PHONY: ci
ci: ci.prepare test assemble.debug

.PHONY: help
help: helpAndroidBase help-plugin help-demo
	@echo ""
	@echo "make dep                          ~> show all depend implementation"
	@echo "make test.coverage                ~> run all test coverage"
	@echo "make test                         ~> run all test"
	@echo "make ci                           ~> run fast as CI"
	@echo ""
	@echo "more task see Makefile!"
