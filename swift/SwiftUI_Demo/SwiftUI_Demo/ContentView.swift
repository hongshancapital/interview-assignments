//
//  ContentView.swift
//  SwiftUI_Demo
//
//  Created by mazb on 2022/9/2.
//

import SwiftUI

struct ContentView: View {
    @StateObject private var listVM = MListViewModel()
    
    var body: some View {
        if listVM.isLoading && listVM.viewInfos.isEmpty {
            ProgressView().background(Color.white)
        }
        else{
            List{
                ForEach(listVM.viewInfos, id: \.trackId){ viewInfo in
                    MAppListCell(viewInfo: viewInfo)
                        .listRowBackground(Color(red: 0.95, green: 0.95, blue: 0.95, opacity: 1.0))
                }

                
                //上拉加载更多
                Button(action: loadMore) {
                    Text(self.listVM.isLoading ? "努力加载中..." : self.listVM.isAllLoaded ? "已经到底喽" : "")
                        .foregroundColor(Color.secondary)
                        .multilineTextAlignment(.center)
                }
                .onAppear {
                    DispatchQueue.global(qos: .background).asyncAfter(deadline: DispatchTime(uptimeNanoseconds: 100)) {
                        self.loadMore()
                    }
                }
            }
            .onAppear(){
                UITableView.appearance().separatorStyle = .none
                UITableViewCell.appearance().selectionStyle = .none
            }
        }
    }
    
    func loadMore() {
        listVM.loadMore()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
