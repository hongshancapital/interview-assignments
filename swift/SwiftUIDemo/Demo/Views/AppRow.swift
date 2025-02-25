//
//  AppRow.swift
//  Demo
//
//  Created by 石超 on 2022/6/22.
//

import SwiftUI

struct AppRow: View {
    @EnvironmentObject var modelData: ModelData
    
    var app: AppModel?
    var appIndex: Int {
        guard app != nil else { return 0 }
        guard modelData.apps.count != 0 else { return 0 }
        
        return modelData.apps.firstIndex(where: { $0.id == app!.id })!
    }
    
    var body: some View {
        VStack() {
            HStack {
                AsyncImage(url: URL(string: app?.imageUrlStr ?? "")) { image in
                    image.resizable().cornerRadius(10)
                } placeholder: {
                    ProgressView()
                }
                .frame(width: 70, height: 70)
                
                VStack(alignment: .leading) {
                    Text(app?.name ?? "")
                        .lineLimit(1)
                        .font(.headline)
                    
                    Spacer()
                    
                    Text(app?.description ?? "")
                        .lineLimit(2)
                        .font(.subheadline)
                }
                
                Spacer()
                
                FavoriteButton(isSet: $modelData.apps[appIndex].isFavorite) {
                    if !(app!.isFavorite)  {
                        modelData.favoriteApps.append(app!.identifer)
                    }
                }
            }
            .padding(EdgeInsets(top: 10, leading: 10, bottom: 10, trailing: 10))
        }
        .background(RoundedRectangle(cornerRadius: 10)
        .foregroundColor(.white))
    }
}

struct AppRow_Previews: PreviewProvider {
    static var apps = ModelData().apps
    
    static var previews: some View {
        Group {
            AppRow(app: apps[0])
            AppRow(app: apps[1])
        }
        .previewLayout(.fixed(width: 300, height: 70))
    }
}
