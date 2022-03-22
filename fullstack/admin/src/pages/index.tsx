import dynamic from "next/dynamic"

const ReactAdmin = dynamic(() => import("admin/index"), {
  ssr: false,
})

const HomePage = () => <ReactAdmin />

export default HomePage