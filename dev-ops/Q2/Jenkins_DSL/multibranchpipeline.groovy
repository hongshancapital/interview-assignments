multibranchPipelineJob('example') {
    branchSources {
        github {
            id('23232323')
            scanCredentialsId('github-ci')
            repoOwner('OwnerName')
            repository('wyangsun/ECS-CICD')
        }
    }
    orphanedItemStrategy {
        discardOldItems {
            numToKeep(20)
        }
    }
}