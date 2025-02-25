//
//  File.swift
//  SwiftUIApp
//
//  Created by Univex on 2022/5/2.
//

import SwiftUI

struct HomeListView: View {
    @State var footerShow = false
    @State var finshed = false
    @State var page = 1
    @State var list: [Home] = []
    
    var body: some View {
        NavigationView {
            List {
                ForEach(list) { home in
                    HomeCell(home: home)
                }
                if footerShow {
                    ListFooterView(finished: finshed, show: footerShow).task {
                        await loadMore()
                    }
                }
            }
            .task {
                await refresh()
            }
            .refreshable {
                await refresh()
            }
            .navigationTitle("App")
        }
        .navigationViewStyle(.stack)
    }
    
    func fetchList(_ params: [String:Any]) async -> Void {
        let(homeList, state) = await APIManager.fetchHomeList(params: params)
        if state == .success {
            if page == 1 {
                list = homeList?.list ?? []
            }
            else {
                list += homeList?.list ?? []
            }
            footerShow = list.count > 0
            if list.count >= homeList?.total ?? 10  {
                page = 1
                finshed = true
            }
        }
    }
    
    func refresh() async -> Void {
        page = 1
        let params = ["page":page, "size":10]
        await fetchList(params)
        page += 1
        finshed = false
    }
    
    func loadMore() async -> Void {
        if !finshed {
            footerShow = true
            let params = ["page":page, "size":10]
            await fetchList(params)
            page += 1
        }
    }
}
