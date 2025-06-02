# check env set ANDROID_HOME
checkEnvAndroidHome:
ifndef ANDROID_HOME
	@ echo Environment variable ANDROID_HOME is not set
	exit 1
endif

.PHONY: env
env:
	@echo "================ env start ================"
	@echo "ROOT_PWD                         ${ROOT_PWD}"
	@echo "ENV_GRADLE_WRAPPER_EXEC          ${ENV_GRADLE_WRAPPER_EXEC}"
	@echo "================ env end ================"
	@echo ""
	${ENV_GRADLE_WRAPPER_EXEC} --version

# init this project
.PHONY: init
init:
	@${ENV_GRADLE_WRAPPER_EXEC} clean buildEnvironment --warning-mode all

.PHONY: cleanRoot
cleanRoot:
	${ENV_GRADLE_WRAPPER_EXEC} clean

.PHONY: cleanGradleBuildAndIdea
cleanGradleBuildAndIdea:
	${ENV_GRADLE_WRAPPER_EXEC} clean cleanBuildCache cleanIdea

.PHONY: adbCrash
adbCrash: checkEnvAndroidHome
	adb shell dumpsys dropbox --print data_app_crash

.PHONY: helpAndroidBase
helpAndroidBase:
	@echo "=> $(ROOT_PWD)/z-android-base.mk : android base task"
	@echo "make init                    ~> init this project for check base build error"
	@echo "make cleanRoot               ~> clean root"
	@echo "make cleanGradleBuildAndIdea ~> clean root"
	@echo "make adbCrash                ~> show last crash info"
	@echo ""