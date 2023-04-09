//
//  ContentView.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import SwiftUI

enum ViewState {
    case idle
    case refreshing
    case loadMore
    case error
}

struct AppsView: View {
    enum Constants {
        static let listCellInsets = EdgeInsets(top: 5, leading: 16, bottom: 5, trailing: 16)
        static let pageBackgroundColor = Color(red: 244/255, green: 244/255, blue: 247/255)
        static let pageTitle = "App"
        static let appIconSize: CGFloat = 58
        static let appIconCornerRadius: CGFloat = 10
        static let appIconBorderWidth: CGFloat = 0.5
        static let appTextVerticalSacing: CGFloat = 6
        static let appNameFontSize: CGFloat = 20
        static let appDescriptionFontSize: CGFloat = 14
        static let appNameLineLimit =  1
        static let appDescriptionLineLimit = 2
        static let favoriteNormalColor = Color(red: 145/255, green: 145/255, blue: 145/255)
        static let favoriteNormalIconName = "heart"
        static let favoriteColor = Color(red: 1, green: 67/255, blue: 54/255)
        static let favoriteIconName = "heart.fill"
        static let appCellCornerRadius: CGFloat = 10
        static let appCellContentInsets = EdgeInsets(top: 16, leading: 16, bottom: 16, trailing: 16)
        static let appCellHorizationSpacing: CGFloat = 10
    }
    
    @EnvironmentObject var viewModel: AppsViewModel
    
    var body: some View {
        NavigationView {
            VStack(alignment: .leading) {
                if viewModel.apps.count == .zero {
                    ProgressView()
                        .onAppear {
                            viewModel.doRefresh()
                        }
                } else {
                    List {
                        ForEach(0..<viewModel.apps.count, id: \.self) { index in
                            appCell(app: viewModel.apps[index], index: index)
                                .onAppear {
                                    if index == self.viewModel.apps.count - 1 {
                                        viewModel.viewState = .loadMore
                                        viewModel.loadMore()
                                    }
                                }
                        }
                        .listRowBackground(Color.clear)
                        .listRowSeparator(.hidden)
                        .listRowInsets(Constants.listCellInsets)
                        
                        if viewModel.showLoadingMore, viewModel.viewState == .loadMore  {
                            Color.clear
                                .overlay {
                                    LoadingMoreView(noMoreData: viewModel.noMoreData)
                                }
                                .listRowBackground(Color.clear)
                                .listRowSeparator(.hidden)
                        }
                    }
                    .listStyle(.plain)
                    .background(Constants.pageBackgroundColor)
                    .refreshable {
                        await viewModel.refresh()
                    }
                    
                    
                }
            }
            .navigationBarTitle(Text(Constants.pageTitle))
        }
    }
    
    @ViewBuilder
    func appCell(app: AppModel, index: Int) -> some View {
        HStack(spacing: Constants.appCellHorizationSpacing) {
            AsyncImage(url: URL(string: app.artworkUrl512)) { image in
                image.resizable()
                    .scaledToFit()
                    .frame(width: Constants.appIconSize, height: Constants.appIconSize)
                    .cornerRadius(Constants.appIconCornerRadius)
                    .overlay {
                        RoundedRectangle(cornerRadius: Constants.appIconCornerRadius, style: .continuous)
                            .stroke(.gray, lineWidth: Constants.appIconBorderWidth)
                    }
            } placeholder: {
                ProgressView()
            }
            
            VStack(alignment: .leading, spacing: Constants.appTextVerticalSacing) {
                Text(app.trackName)
                    .font(.system(size: Constants.appNameFontSize, weight: .medium))
                    .lineLimit(Constants.appNameLineLimit)
                    .frame(minWidth: .zero, maxWidth: .infinity, alignment: .leading)
                Text(app.description)
                    .font(.system(size: Constants.appDescriptionFontSize))
                    .lineLimit(Constants.appDescriptionLineLimit)
                    .frame(minWidth: .zero, maxWidth: .infinity, alignment: .leading)
            }
            
            if app.favorite {
                Image(systemName: Constants.favoriteIconName)
                    .foregroundColor(Constants.favoriteColor)
                    .onTapGesture {
                        viewModel.favoriteAppToggle(app: app)
                    }
            } else {
                Image(systemName: Constants.favoriteNormalIconName)
                    .foregroundColor(Constants.favoriteNormalColor)
                    .onTapGesture {
                        viewModel.favoriteAppToggle(app: app)
                    }
            }
            
        }
        .padding(Constants.appCellContentInsets)
        .background(.white)
        .cornerRadius(Constants.appCellCornerRadius)
    }
}

struct ContentView_Previews: PreviewProvider {
    
    @ObservedObject static var appsViewModel = AppsViewModel(appService: AppService(), coreDataStack: CoreDataStack(modelName: "Model"))
    
    static var previews: some View {
        AppsView()
            .environmentObject(appsViewModel)
    }
}
