//
//  AppRow.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct AppRow: View {
    @State var appModel: AppModel
    var body: some View {
        HStack{
            AsyncImage(url: URL(string: appModel.iconUrl)){ image in
                    image.resizable()
            }placeholder: {
                ProgressView()
            }
            .frame(width: 50, height: 50, alignment: .center)
            .cornerRadius(8)
            
            VStack(alignment: .leading) {
                Text(appModel.name)
                    .font(.title)
                    .lineLimit(1)
                Text(appModel.description)
                    .font(.subheadline)
                    .lineLimit(2)
            }
            
            Spacer()
            FavoriteButton(isSet: $appModel.isFavorite)
        }
        .padding(10)
        .background(Color(uiColor: .white))
        .cornerRadius(8)
    
    
    }
}

struct AppRow_Previews: PreviewProvider {
    static var apps = ModelData().apps
    static var previews: some View {
        AppRow(appModel: apps[0])
            .previewLayout(.fixed(width: 300, height: 70))
    }
}
