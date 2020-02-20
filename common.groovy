package common

class CommonUtils {
  static void addDefaults(context) {
      context.with {
        logRotator {
            numToKeep(numbBuildToKeep)
        }
      }
  }
}