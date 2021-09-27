//
//  HomeView.swift
//  scdt-homework
//
//  Created by Chr1s on 2021/9/26.
//

import SwiftUI

// MARK: - 主视图
struct HomeView: View {
    
    @EnvironmentObject var vm: ViewModel
    
    var body: some View {
        NavigationView {
            ZStack {
                Color(.systemGray6).edgesIgnoringSafeArea(.all)
                
                VStack {
                    searchBar()
                        .padding()
                        .background(
                            RoundedRectangle(cornerRadius: 24)
                                .foregroundColor(.clear)
                                .background(Color.gray.opacity(0.3))
                                .clipShape(RoundedRectangle(cornerRadius: 14))
                        )
                        .padding()
                    
                    Spacer()
                }
            }
            .navigationTitle("Search")
        }
    }
}

extension HomeView {
    // MARK: - 搜索框 (Image + Button)
    func searchBar() -> some View {
        HStack {
            Image(systemName: "magnifyingglass")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 20, height: 20)
            
            Button(action: {
                vm.isSearch = true
            }, label: {
                Text("Tap here to search")
                    .foregroundColor(.black.opacity(0.7))
            })
            
            Spacer()
        }
    }
}
