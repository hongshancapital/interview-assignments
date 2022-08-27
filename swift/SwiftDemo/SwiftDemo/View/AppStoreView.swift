//
//  AppStoreView.swift
//  SwiftDemo
//
//  Created by teenloong on 2022/8/27.
//

import SwiftUI

struct AppStoreView: View {
    @ObservedObject private var viewModel = AppStoreViewModel()

    var body: some View {
        ZStack {
            let backgroundColor: Color = Color(#colorLiteral(red: 0.9490196078, green: 0.9490196078, blue: 0.9647058824, alpha: 1))
            backgroundColor.ignoresSafeArea()
            switch viewModel.loadState {
            case .loading:
                ProgressView()
                    .onAppear {
                        viewModel.refresh()
                    }
            case .loaded:
                makeList()
            case .error:
                makeErrorView()
            }
        }
        .navigationBarTitle("APP")
        .alert(isPresented: $viewModel.showErrorAlert, error: viewModel.error, actions: {
            Button(role: .cancel) {
                
            } label: {
                Text("Cancel")
            }
            Button(role: nil) {
                viewModel.loadmore()
            } label: {
                Text("Retry")
            }
        })
    }
    
    func makeList() -> some View {
        List {
            ForEach(viewModel.appInfos) { item in
                AppInfoRowView(item: item, action: {_ in
                    
                })
                .listRowBackground(Color.clear)
                .listRowSeparator(.hidden)
            }
            HStack {
                if viewModel.hasmore {
                    HStack(spacing: 10) {
                        ProgressView()
                            .id(UUID().uuidString)
                        Text("Loading More...")
                    }
                } else {
                    Text("No More Data")
                }
            }
            .frame(maxWidth: .infinity, alignment: .center)
            .listRowBackground(Color.clear)
            .listRowSeparator(.hidden)
            .onAppear {
                viewModel.loadmore()
            }
        }
        .listStyle(.plain)
        .refreshable {
            viewModel.refresh()
        }
    }
    
    func makeErrorView() -> some View {
        VStack {
            Button {
                viewModel.refresh()
            } label: {
                HStack {
                    Image(systemName: "arrow.triangle.2.circlepath")
                    Text("Refresh")
                }
            }
            if let error = viewModel.error {
                Text(error.localizedDescription)
            }
        }
        .padding()
    }
}

#if DEBUG
struct AppListView_Previews: PreviewProvider {
    static var previews: some View {
        AppStoreView()
    }
}
#endif

fileprivate struct AppInfoRowView: View {
    @State private var like: Bool = false
    
    let item: AppInfo
    let action: (Int) -> Void
    
    var body: some View {
        let backgroundColor: Color = Color(#colorLiteral(red: 1, green: 1, blue: 1, alpha: 1))
        HStack {
            AsyncImage(url: item.artworkURL60, content: { image in
                image.resizable()
                    .aspectRatio(contentMode: .fit)
            }, placeholder: {
                ProgressView()
            })
            .frame(width: 50, height: 50)
            .mask(RoundedRectangle(cornerRadius: 10))
            .overlay(RoundedRectangle(cornerRadius: 10).strokeBorder().foregroundColor(Color.gray))
            VStack(alignment: .leading, spacing: 4) {
                Text(item.name ?? "")
                    .font(.system(size: 16, weight: .medium))
                Text(item.description ?? "")
                    .lineLimit(2)
                    .font(.system(size: 12, weight: .regular))
                    .frame(maxHeight: .infinity, alignment: .topLeading)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            Button {
                Store.shared.dispatch(.likeApp(id: item.id, like: !like))
            } label: {
                Image(systemName: like ? "heart.fill" : "heart")
                    .foregroundColor(like ? .red : .gray)
                    .scaleEffect(like ? 1.2 : 1.0)
                    .animation(.spring(), value: like)
            }
        }
        .padding()
        .background(RoundedRectangle(cornerRadius: 10).fill(backgroundColor))
        .onReceive(Store.shared.appState.appStore.$likedApps) { appIds in
            like = appIds.contains(item.id)
        }
    }
}
