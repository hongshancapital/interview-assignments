.PHONY: i
i:
	cd infra && npm i

.PHONY: infra
infra:
	cd infra && touch .env && source .env && cdk deploy --all --require-approval never

.PHONY: cdk.upgrade
cdk.upgrade:
	cd infra && rm -f package-lock.json && npm i
