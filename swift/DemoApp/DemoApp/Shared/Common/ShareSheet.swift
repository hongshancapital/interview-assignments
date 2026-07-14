//
//  DemoContentModel.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//

import UIKit
import SwiftUI


struct ShareSheet: UIViewControllerRepresentable {
    var items : [Any]
    var excludedActivityTypes: [UIActivity.ActivityType]? = nil
    
    func makeUIViewController(context: Context) -> UIActivityViewController {
        let controller = UIActivityViewController(activityItems: items, applicationActivities: nil)
        controller.excludedActivityTypes = excludedActivityTypes
        return controller
    }
    
    func updateUIViewController(_ uiViewController: UIActivityViewController, context: Context) {
        
    }
}
