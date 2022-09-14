//
//  DemoAppApp.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/14.
//

import SwiftUI

@main
struct DemoAppApp: App {
    var body: some Scene {
        WindowGroup {
            DemoContentView()
        }
    }
}


struct TimePullRefreshView: View {
     @State var isShowing: Bool = false
       @State var array = ["date"]
       var body: some View {
          NavigationView {
            List(array, id: \.self) { text in
              Text(text)
            }
            .navigationBarTitle("刷新时间")
          }
          .background(PullToRefreshNavigationView(action: {
            DispatchQueue.main.asyncAfter(deadline: .now() + 1) {
              self.isShowing = false
              //self.array.append(Date().description)
               self.array.insert(Date().description, at: 0)
            }
          }, isShowing: $isShowing))
        }
}
