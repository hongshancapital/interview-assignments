//
//  AppListRow.swift
//  AppListDemo
//
//  Created by Kimi on 2023/3/7.
//

import SwiftUI

struct AppListRow: View {
    @Binding var appModel: AppModel
    
    var body: some View {
        VStack {
            Spacer()
            
            HStack {
                AppIconImage(urlString: appModel.appIconUrlString)
                    .frame(width: 50, height: 50)
                
                VStack(alignment: .leading, spacing: 4){
                    Text(appModel.appName)
                        .font(.headline)
                        .bold()
                        .lineLimit(1)
                        .minimumScaleFactor(0.6)
                    
                    Text("\(appModel.appDescription.replacingOccurrences(of: "\n", with: ""))")
                        .font(.caption)
                        .lineLimit(2)
                }
                
                Spacer()
                
                FavouriteButton(appModel: $appModel)
                    .frame(width: 30, height: 30)
                
            }
            
            Spacer()
        }
        .padding(EdgeInsets(top: 0, leading: 15, bottom: 0, trailing: 12))
        .background(.white)
        .clipShape(RoundedRectangle(cornerRadius: 10))
    }
}

private struct AppIconImage: View {
    var urlString: String
    
    var body: some View {
        AsyncImage(url: URL(string: urlString)) { image in
            image.resizable()
                .aspectRatio(contentMode: .fit)
                .clipShape(RoundedRectangle(cornerRadius: 6))
                .overlay {
                    RoundedRectangle(cornerRadius: 6)
                        .stroke(Color("color_image_border"),lineWidth: 0.5)
                }
        } placeholder: {
            ProgressView().progressViewStyle(.circular)
        }
    }
}

private struct FavouriteButton: View {
    @EnvironmentObject private var viewModel: AppListViewModel
    @Binding var appModel: AppModel
    
    var body: some View {
        //fix Button tap area apply to all row
        Image(systemName: appModel.isFavourite ? "heart.fill" : "heart")
            .resizable()
            .scaledToFit()
            .frame(width: 18, height: 18)
            .scaleEffect(appModel.isFavourite ? 1.22 : 1)
            .animation(.easeOut(duration: 0.3), value: appModel.isFavourite)
            .foregroundColor(appModel.isFavourite ? Color("color_fav_red") : Color("color_fav_gray"))
            .onTapGesture {
                Task {
                    await viewModel.toggleFavouriteApp(appModel)
                }
            }
    }
}

struct AppListRow_Previews: PreviewProvider {
    @State static var appModel = AppModel(
        id: "testId",
        appName: "Bumble - Dating. Friends. Bizz",
        appDescription: "Millions of people have signed up for Bumble to start building valuable relationships, finding friends, and making empowered connections. ",
        appIconUrlString:"https://is1-ssl.mzstatic.com/image/thumb/Purple122/v4/cd/c4/7b/cdc47b18-487a-fbe3-05c4-9ca99fde903d/AppIcon-1x_U007emarketing-0-7-0-85-220.png/100x100bb.jpg",
        isFavourite: true
    )
    
    static var previews: some View {
        AppListRow(appModel: $appModel)
            .environmentObject(AppListViewModel(pageCount: 10))
            .previewLayout(.fixed(width: 390, height: 80))
    }
}
