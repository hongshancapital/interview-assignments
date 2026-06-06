/*
See LICENSE folder for this sample’s licensing information.

Abstract:
A view showing the details for a landmark.
*/

import SwiftUI

struct HSDetail: View {
    var model: HSModel

    var body: some View {
        ScrollView {
            MapView(coordinate: model.locationCoordinate)
                .ignoresSafeArea(edges: .top)
                .frame(height: 300)
            HSCircleImage(image: model.image)
                .offset(y: -130)
                .padding(.bottom, -130)

            VStack(alignment: .leading) {
                Text(model.name)
                    .font(.title)
                HStack {
                    Text(model.park)
                    Spacer()
                    Text(model.state)
                }
                .font(.subheadline)
                .foregroundColor(.secondary)
                Divider()
                Text("About \(model.name)")
                    .font(.title2)
                Text(model.description)
            }
            .padding()
        }
        .navigationTitle(model.name)
        .navigationBarTitleDisplayMode(.inline)
    }
}

struct LandmarkDetail_Previews: PreviewProvider {
    static var previews: some View {
        HSDetail(model: hsArr[0])
    }
}
