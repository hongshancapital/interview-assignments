import * as cdk from '@aws-cdk/core';
import { CfnWebACL, CfnIPSet } from "@aws-cdk/aws-wafv2";



export type WafProps = {
  /**
   * List of IPv4 addresses to allow
   */
  ipAllowList?: Array<string>;
  /**
   * Default action to take if no rules where activated; block or allow?
   *
   * @default block
   */
  defaultAction?: "block" | "allow";
  /**
   * Scope of the WAF - REGIONAL or CLOUDFRONT.
   *
   * Choose CLOUDFRONT when the WAF is for use with a CloudFront distribution and
   * REGIONAL for everything else (an ELB for example).
   *
   * @default REGIONAL
   */
  scope?: "REGIONAL" | "CLOUDFRONT";
  };
/**
 * Web Application Firewall (WAF) construct.
 *
 * Supports:
 * - Scopes: CloudFront and regional
 * - Rules:
 *   - Ip allow list
 */
export class Waf extends cdk.Construct {
  public readonly webAcl: CfnWebACL;

  constructor(scope: cdk.Construct, id: string, props: WafProps) {
    super(scope, id);
    // Extract configuration
    const {
      ipAllowList,
      defaultAction = "block",
      scope: wafScope = "REGIONAL",
    } = props;

    // List of WAF rules to apply
    const rules = [];
    if (ipAllowList) {
      rules.push(this.createIpAllowListRule(wafScope, ipAllowList));
    }
    // Create WAF
    this.webAcl = new CfnWebACL(this, "Waf", {
      scope: wafScope,
      defaultAction:
        defaultAction === "block"
          ? this.createBlockAction()
          : this.createAllowAction(),
      visibilityConfig: {
        cloudWatchMetricsEnabled: true,
        sampledRequestsEnabled: true,
        metricName: id,
      },
      rules,
    });
  }

  private createBlockAction() {
    return {
      block: {},
    };
  }

  private createAllowAction() {
    return {
      allow: {},
    };
  }

  private createIpAllowListRule(scope: string, allowList: Array<string>): any {
    const allowListIpSet = new CfnIPSet(this, "AllowList", {
      ipAddressVersion: "IPV4",
      scope,
      addresses: allowList,
    });
    return {
      name: "ip-allow-list",
      priority: 0,
      action: this.createAllowAction(),
      statement: {
        ipSetReferenceStatement: { arn: allowListIpSet.attrArn },
      },
      visibilityConfig: {
        cloudWatchMetricsEnabled: true,
        sampledRequestsEnabled: true,
        metricName: "ip-allow-list",
      },
    };
  }
}

export class AwsWafPolicyStack extends cdk.Stack {
  constructor(scope: cdk.Construct, id: string, props?: cdk.StackProps) {
    super(scope, id, props);
  //Create walfpros type
  new Waf(this, "devops-waf", {
  ipAllowList: ["10.0.0.2/32"],
});
  }
}
