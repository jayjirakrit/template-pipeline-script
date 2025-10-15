# Jenkins Pipeline Job Creation System

## Architecture Overview
This repository contains a modular Jenkins pipeline framework that enables dynamic job creation and execution through a template-based approach. The system separates configuration, job definition, and execution logic to provide flexible and reusable CI/CD pipelines. Utilizing concept Infrastructure as a Code (IaaC) focusing on streamline IT operation process, standardize, and automation.

### 1. Pipeline Script Template
**File:** `job-builder.groovy`

- **Type:** Parameterized Pipeline Script
- **Parameters:**
  - `git-path` - Git repository path
  - `pipeline-parameter-file-path` - Path to pipeline configuration file
- **Function:** Creates new jobs using Job DSL from `job-dsl.groovy`

### 2. Job DSL Script
**File:** `job-dsl.groovy`

- **Type:** Job DSL Script
- **Function:** 
  - Receives parameters from pipeline configuration
  - Creates specific job types based on configuration

### 3. Configuration Data
**File:** `pipeline-parameter.yaml`

- **Format:** YAML configuration file
- **Purpose:** Provides dynamic configuration data including:
  - Job name
  - Job DSL file path
  - OCP (OpenShift) configurations
  - Other job-specific parameters

### 4. Pipeline Execution Template
**File:** `template.groovy`

- **Function:** Defines actual execution stages and steps
- **Stages:**
  - Prepare environment
  - Build artifact
  - Build image
  - Additional execution steps

## Key Plugins Required

- **DSL Job Plugin** - For Job DSL functionality
- **Utilities Step Plugin** - For additional pipeline utilities

## Code Example

```groovy
// Read YAML File as Object
def configVal = readYaml file: "full path of the yml file"
```

## Reference
- https://github.com/jenkinsci/job-dsl-plugin/wiki/User-Power-Moves
