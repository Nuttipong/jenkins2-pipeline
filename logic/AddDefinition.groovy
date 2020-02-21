package logic

interface AddDefinition {
  void addDefinition(String repo, String branch, boolean lightweight, String jenkinsFile)
}