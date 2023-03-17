//
//  ContentView.swift
//  SwiftDemo
//
//  Created by 彭军涛 on 2022/7/10.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var model: DemoViewModel
    var body: some View {
        NavigationView {
            if model.model.results == nil {
                Group {
                    if model.showErrorMessage == nil {
                        ProgressView()

                    } else {
                        Text(model.showErrorMessage ?? "")
                    }

                }
                .navigationTitle("App")
            } else {
                List {
                    ForEach(model.listData,id:\.bundleId) { value in
                        SingleCellView(data: value)
                            .padding(.init(top: 0, leading: 0, bottom: 0, trailing: 0))
                            .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
                    }

                    LoadingView()
                }
                .refreshable {
                    await model.refresh()
                }

                .onAppear(perform: {
                    UITableView.appearance().separatorStyle = .none
                })
                .navigationTitle("App")
            }
        }

    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
            ContentView().environmentObject(DemoViewModel())
    }
}
