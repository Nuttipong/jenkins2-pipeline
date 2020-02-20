package models


interface AddChoiceParam {
  void addChoiceParam(Object[] args)
}

interface AddConfig {
  void addConfig(String sortMode, String filter)
}

interface AddStringParam {
  void addStringParam(Object[] args)
}

interface AddDefinition {
  void addDefinition(String repo, String branch, boolean lightweight)
}

interface AddLogRotator {
  void addLogRotator(int number)
}