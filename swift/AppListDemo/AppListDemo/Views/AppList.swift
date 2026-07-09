//
//  AppList.swift
//  mahefei-swift-answer
//
//  Created by Mars on 2023/2/2.
//

import SwiftUI



struct AppList: View {
    
    //由于是本地模拟数据，我就不做模拟刷新和分页了,大概就这个意思
    var body: some View {
        NavigationView () {
           
            List(){
                
                ForEach(appData, id: \.trackId) { app in
                    AppRow(app: app)
                    .listRowSeparator(.hidden)
                    .listRowInsets(EdgeInsets())
                }
                
                HStack {
                    //Text("Loading...")
                    Text("No more data.")
                }
                .listRowInsets(EdgeInsets())
                .frame(maxWidth: .infinity, minHeight: 60)
                .background(Color(UIColor.systemGroupedBackground))
                .onAppear(){
                    print("上拉加载了")
                }
                
            }
            .navigationBarTitle("App")
            .refreshable {
                print("下啦刷新了")
            }
        }
        
    }
}

struct AppList_Previews: PreviewProvider {
    static var previews: some View {
        AppList()
    }
}
