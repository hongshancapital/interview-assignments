//
//  ContentView.swift
//  SwiftUIDemo
//
//  Created by chenghao on 2022/5/11.
//

import SwiftUI

struct ContentView: View {
    @EnvironmentObject var storage: DataStorage
    @State var isLoading = false
    
    var body: some View {
        if storage.links.count == 0 {
            ProgressView().onAppear(perform: loadMore)
        } else {
            NavigationView {
                List {
                    ForEach(storage.links) { linkModel in
                        ListRow(dataModel: linkModel)
                            .listRowBackground(EmptyView())
                            .listRowSeparator(.hidden)
                            .cornerRadius(10)
                    }
                }
                
                HStack(alignment: .center) {
                    Spacer()
                    if storage.hasMore {
                        ProgressView().padding(.trailing, 4)
                    }
                    Text(storage.hasMore ? "Loading..." : "No more data.")
                        .foregroundColor(.init(uiColor: UIColor.placeholderText))
                        .onAppear(perform: loadMore)
                    Spacer()
                }
                .listRowBackground(EmptyView())
                .listRowSeparator(.hidden)
            }
            .listStyle(.plain)
            .background(Color(uiColor: UIColor.systemGroupedBackground))
            .navigationTitle("App")
            .refreshable {
                if !isLoading {
                    isLoading = true
                    storage.refersh {
                        isLoading = false
                    }
                }
            }
            .navigationViewStyle(.stack)
        }
    }
    
    func loadMore() {
        if !isLoading {
            isLoading = true
            storage.loadMore {
                isLoading = false
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(DataStorage(dataProvider: FileDataProvider()))
    }
}
