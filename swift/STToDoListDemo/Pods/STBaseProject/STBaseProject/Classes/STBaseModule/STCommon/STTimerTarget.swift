//
//  STTimerTarget.swift
//  STBaseProject
//
//  Created by stack on 2018/01/22.
//  Copyright © 2018 ST. All rights reserved.
//

import UIKit

public class STTimerTarget: NSObject {

    private weak var target: AnyObject?

    public init(aTarget: AnyObject) {
        super.init()
        self.target = aTarget
    }

    public override func forwardingTarget(for aSelector: Selector!) -> Any? {
        return self.target
    }
}
