//
//  ListView.swift
//  InterView
//
//  Created by 黑旭鹏 on 2022/4/2.
//

import SwiftUI

struct ListView: View {
    @State private var dataSource: [Model] = []
    @State private var number: Int = 0
    @State private var isLoading: String = "isLoading"
    @State private var isRefreshing: Bool = false
    @State private var isLoadMore: Bool = false


    var body: some View {
        NavigationView {
            List {
                Section {
                    if (dataSource.count == 0) {
                        NoDataRow()
                            .listRowSeparator(.hidden)
                            .listRowBackground(Color("customColor"))
//                            .onAppear(perform: fetchRemoteImage)
                    }else{
                        ForEach(dataSource){model in
                            ListRow(model: model)
                                .listRowSeparator(.hidden)
                                .listRowBackground(Color("customColor"))
                        }
                    }
                } header: {

                } footer: {
                    HStack(spacing: 10){
                        ProgressView()
                        Text(isLoading)
                            .onAppear(perform: {
                                fetchRemoteImage()
                            })
                            .font(.headline)

                    }
                    .frame(maxWidth:.infinity, maxHeight: .infinity, alignment: .center)
                }
            }
            .listStyle(.grouped)
            .navigationTitle(Text("App"))
            .refreshable {
                number = 0
                isRefreshing = true
                fetchRemoteImage()
            }
        }
    }
    
    func fetchRemoteImage() {
        self.number = self.number+10
        if self.number >= modelData.results.count {
            self.number = modelData.results.count
            isLoadMore = false
            isLoading = "No more data."
        }else{
            isLoading = "isLoading"
            let t = DispatchTime.now() + 1
            DispatchQueue.main.asyncAfter(deadline: t) {
                if isRefreshing {
                    dataSource.removeAll()
                    isRefreshing = false
                }
                
                for index in 0..<number {
                    dataSource.append(modelData.results[index])
                }
            }
        }
    }
}


struct ListView_Previews: PreviewProvider {
    static var previews: some View {
        ListView()
    }
}
