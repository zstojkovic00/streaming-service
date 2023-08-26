import React from 'react'
import {ComponentPreview, Previews} from '@react-buddy/ide-toolbox'
import {PaletteTree} from './palette'
import List from "../components/list/List";

const ComponentPreviews = () => {
    return (
        <Previews palette={<PaletteTree/>}>
            <ComponentPreview path="/List">
                <List/>
            </ComponentPreview>
        </Previews>
    )
}

export default ComponentPreviews