//
//  DemoContentView.swift
//  DemoApp
//
//  Created by Meteor 丶Shower on 2022/5/18.
//

import SwiftUI
import Refresh

struct DemoContentView: View {
        
    @State private var selecteState: [String:String] = ["":""]
    @StateObject private var dataModel = DemoContentViewModel()
    
    var body: some View {
        NavigationView {
            VStack {
              ScrollView {
                  LazyVGrid(columns: [GridItem(.flexible())], spacing: 15) {
                      ForEach(0 ..< 1) { index in
                          Section {
                              ForEach(self.dataModel.results) { item in
                                  RoundedRectangle(cornerRadius: 8)
                                      .foregroundColor(Color.white)
                                      .frame(height: 84)
                                      .overlay(DemoContentCell(model: item, selecteState: self.$selecteState).frame(height: 84))
                              }
                          }
                      }
                  }
                  .padding()
                  .background(Color(UIColor.systemGroupedBackground))
              }
            }
            .clipped()
            .navigationTitle("App")
            .navigationBarTitleDisplayMode(.automatic)
            .background(Color(UIColor.systemGroupedBackground))
        }
    }
}

struct DemoContentView_Previews: PreviewProvider {
    static var previews: some View {
        DemoContentView()
    }
}
