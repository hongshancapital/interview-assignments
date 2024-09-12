/*
See LICENSE folder for this sample’s licensing information.

Abstract:
A single row to be displayed in a list of landmarks.
*/

import SwiftUI

struct HSRow: View {
    var model: HSModel

    var body: some View {
        HStack {
            HStack{
                HStack{
                    model.image
                        .resizable()
                        .frame(width: 50, height: 50)
                        .border(Color.gray, width: 1)
                        .cornerRadius(8)
                    VStack(alignment: .leading, spacing: 12, content: {
                        Text(model.name)
                            .fontWeight(.bold)

                        Text(model.park)
                            .font(.caption)
                            .foregroundColor(.gray)
                    })
                }
                .frame(maxWidth: .infinity, alignment: .leading)
                
                HStack{
                    if (model.isFavorite) {
                        Image("favorite")
                            .resizable()
                            .frame(width: 25, height: 25)
                            .cornerRadius(8)
                    }else{
                        Image("noFavorite")
                            .resizable()
                            .frame(width: 25, height: 25)
                            .cornerRadius(8)
                    }
                }
                .padding(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 15))
            }
            .background(Color.white)
            .cornerRadius(8)
            .padding(EdgeInsets(top: 0, leading: 15, bottom: 10, trailing: 10))
            Spacer()
        }
        .background(Color.init(red: 245/255.0, green: 245/255.0, blue: 245/255.0))
    }
}

struct LandmarkRow_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            HSRow(model: hsArr[0])
            HSRow(model: hsArr[1])
        }
        .previewLayout(.fixed(width: 300, height: 70))
    }
}
