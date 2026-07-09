//
//  AppRow.swift
//  mahefei-swift-answer
//
//  Created by Mars on 2023/2/2.
//

import SwiftUI

struct AppRow: View {
    
    var app:DataItem
    
    var body: some View {
        VStack {
            HStack {
                AppImage(url: app.artworkUrl512)
                VStack(alignment: .leading) {
                    Text(app.trackName).font(.title3).bold().lineLimit(1)
                    Text(app.description).lineLimit(2)
                }
                Spacer()
                AppButton(app: app)
            }
            .padding()
            .background(Color.white)
            .cornerRadius(10)
            
        }
        .padding(EdgeInsets(top: 0, leading: 0, bottom: 10, trailing: 0))
        .background(Color(UIColor(red: 0.95, green: 0.95, blue: 0.97, alpha: 1)))
        
        
    }
}

struct AppRow_Previews: PreviewProvider {
    static var previews: some View {
        
        AppRow(app: appData[0])
    }
}
